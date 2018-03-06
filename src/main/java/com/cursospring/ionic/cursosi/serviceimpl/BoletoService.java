package com.cursospring.ionic.cursosi.serviceimpl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.cursospring.ionic.cursosi.model.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instante) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(instante);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataPagamento(cal.getTime());
	}

}
