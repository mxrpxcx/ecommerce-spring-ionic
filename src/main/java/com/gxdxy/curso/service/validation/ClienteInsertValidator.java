package com.gxdxy.curso.service.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.gxdxy.curso.domain.enums.TipoCliente;
import com.gxdxy.curso.dto.ClienteNewDTO;
import com.gxdxy.curso.resources.exception.FieldMessage;
import com.gxdxy.curso.service.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO dto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if(dto.getTipo().equals(TipoCliente.PESSOAFISICA.getCodigo()) 
				&& !BR.isValidCPF(dto.getDocumento())) {
			list.add(new FieldMessage("documento","CPF inválido"));
		}
		
		if(dto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCodigo()) 
				&& !BR.isValidCNPJ(dto.getDocumento())) {
			list.add(new FieldMessage("documento","CNPJ inválido"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMensagem()).addPropertyNode(e.getNomeCampo())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}