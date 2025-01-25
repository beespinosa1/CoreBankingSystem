package com.banquito.cbs.aplicacion.transaccion.servicio;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;
import com.banquito.cbs.aplicacion.transaccion.repositorio.*;
import com.banquito.cbs.aplicacion.transaccion.modelo.Diferido;
import com.banquito.cbs.aplicacion.transaccion.modelo.Transaccion;

@Service
public class DiferidoServcio {
    private final DiferidoRepositorio diferidoRepositorio;

    private static final String ESTADO_PAGADO = "PAG";
    private static final String ESTADO_PENDIENTE = "PEN";

    public DiferidoServcio(DiferidoRepositorio diferidoRepositorio) {
        this.diferidoRepositorio = diferidoRepositorio;
    }

    public void crearDiferido(Transaccion transaccion, Integer cuotas) {
        Diferido diferido = new Diferido();

        diferido.setTransaccionId(transaccion.getId());
        diferido.setCuotas(cuotas);
        diferido.setCuotasCanceladas(0);
        diferido.setTazaInteres(transaccion.getTazaInteres());

        BigDecimal principal = transaccion.getValor();
        BigDecimal tasaAnual = transaccion.getTazaInteres(); 

        BigDecimal tasaAnualDecimal = tasaAnual.divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP);
        BigDecimal tasaMensual = tasaAnualDecimal.divide(BigDecimal.valueOf(12), 6, RoundingMode.HALF_UP);
        BigDecimal factorCompuesto = BigDecimal.ONE.add(tasaMensual).pow(cuotas);
        BigDecimal valorDeuda = principal.multiply(factorCompuesto).setScale(2, RoundingMode.HALF_UP);

        diferido.setValorDeuda(valorDeuda);
        diferido.setValorCuota(valorDeuda.divide(new BigDecimal(cuotas), 2, RoundingMode.HALF_UP));
        diferido.setValorInteres(valorDeuda.subtract(principal));
        diferido.setValorRestante(valorDeuda);
        diferido.setEstado(DiferidoServcio.ESTADO_PENDIENTE);

        this.diferidoRepositorio.save(diferido);
    }
}
