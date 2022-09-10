package com.tcs.company.rest.controller;

import com.tcs.company.domain.entity.ItemPedido;
import com.tcs.company.domain.entity.Pedido;
import com.tcs.company.domain.enums.StatusPedido;
import com.tcs.company.rest.dto.AtualizacaoStatusPedidoDTO;
import com.tcs.company.rest.dto.InformacaoItemPedidoDTO;
import com.tcs.company.rest.dto.InformacoesPedidoDTO;
import com.tcs.company.rest.dto.PedidoDTO;
import com.tcs.company.service.PedidoService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody @Valid PedidoDTO dto) {
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }

    @GetMapping("{id}")
    public InformacoesPedidoDTO getById(@PathVariable Integer id) {
        return service
                .obterPedidoCompleto(id)
                .map(p -> converter(p))
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Pedido não encontrado!"));
    }

    private InformacoesPedidoDTO converter (Pedido pedido) {
        return InformacoesPedidoDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .items(converter(pedido.getItens()))
                .build();
    }

    @PatchMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateStatus(
            @PathVariable Integer id,
            @RequestBody @Valid AtualizacaoStatusPedidoDTO dto
    ) {
        String novoStatus = dto.getNovoStatus();
        service.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
    }

    private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens) {
        if (CollectionUtils.isEmpty(itens)) {
            return Collections.emptyList();
        }
        return itens
                .stream()
                .map(item -> InformacaoItemPedidoDTO
                    .builder()
                        .descricaoProduto((item.getProduto().getDescricao()))
                        .precoUnitario(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade())
                        .build()
                )
                .collect(Collectors.toList());
    }

}
