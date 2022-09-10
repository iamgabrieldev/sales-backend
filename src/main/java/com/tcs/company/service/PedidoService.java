package com.tcs.company.service;

import com.tcs.company.domain.entity.Pedido;
import com.tcs.company.domain.enums.StatusPedido;
import com.tcs.company.rest.dto.PedidoDTO;
import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);
    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
