package com.example.serverrest.service;

import com.example.serverrest.config.ConfigProperties;
import com.example.serverrest.dto.response.OrderResponseDto;
import com.example.serverrest.exception.DataProcessingException;
import com.example.serverrest.mapper.ThriftMapper;
import communication.InvalidOperationException;
import communication.OrderServiceThrift;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final ConfigProperties config;
    private final TSocket socket;
    private final TProtocol protocol;
    private final TMultiplexedProtocol multiplexedProtocol;
    private final OrderServiceThrift.Client client;
    private final ThriftMapper mapper;

    public OrderService(ConfigProperties config, ThriftMapper mapper) throws TTransportException {
        this.config = config;
        socket =  new TSocket(config.host(), config.port());
        this.mapper = mapper;
        protocol = new TBinaryProtocol(socket);
        multiplexedProtocol = new TMultiplexedProtocol(protocol, config.service().order());
        client = new OrderServiceThrift.Client(multiplexedProtocol);
    }

    public OrderResponseDto completeOrder(Long customerId) {
        try (socket){
            socket.open();
            return mapper.mapToOrderDto(client.completeOrder(customerId));
        } catch (InvalidOperationException e) {
            throw new DataProcessingException(e.getMessage(), e);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    public List<OrderResponseDto> getOrdersHistory(Long customerId) {
        try (socket){
            socket.open();
            return client.getOrdersHistory(customerId).stream()
                    .map(mapper::mapToOrderDto)
                    .collect(Collectors.toList());
        } catch (InvalidOperationException e) {
            throw new DataProcessingException(e.getMessage(), e);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }
}
