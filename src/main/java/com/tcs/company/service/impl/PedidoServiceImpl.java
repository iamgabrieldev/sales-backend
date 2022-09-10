package com.tcs.company.service.impl;

import com.tcs.company.domain.entity.Cliente;
import com.tcs.company.domain.entity.ItemPedido;
import com.tcs.company.domain.entity.Pedido;
import com.tcs.company.domain.entity.Produto;
import com.tcs.company.domain.enums.StatusPedido;
import com.tcs.company.domain.repository.Clientes;
import com.tcs.company.domain.repository.ItemsPedido;
import com.tcs.company.domain.repository.Pedidos;
import com.tcs.company.domain.repository.Produtos;
import com.tcs.company.exception.PedidoNaoEncontradoException;
import com.tcs.company.exception.RegraNegocioException;
import com.tcs.company.rest.dto.ItemPedidoDTO;
import com.tcs.company.rest.dto.PedidoDTO;
import com.tcs.company.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // annotation faz com que gere o construtor com argumentos obrigatorios (que contém final)
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos repository;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;
    private final ItemsPedido itemsPedidoRepository;

    @Override
    @Transactional // annotation: ou faz tudo ou faz rollback se acontecer um erro
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de Cliente inválido!"));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItems());
        repository.save(pedido);
        itemsPedidoRepository.saveAll(itemsPedido);

        pedido.setItens(itemsPedido);

        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        repository
                .findById(id)
                .map(pedido -> {
                    pedido.setStatus(statusPedido);
                    return repository.save(pedido);
                })
                .orElseThrow(() -> new PedidoNaoEncontradoException());

    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items) {
        if (items.isEmpty()) {
            throw new RegraNegocioException("Não é possível realizar um pedido sem items!");
        }
        return items
                .stream()
                .map(dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository
                            .findById(idProduto)
                            .orElseThrow(
                                    () -> new RegraNegocioException("Código de Produto inválido! Código: " + idProduto)
                            );
                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                })
                .collect(Collectors.toList());
    }
}
