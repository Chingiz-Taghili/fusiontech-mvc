package com.multishop.fusiontech.services.impls;

import com.multishop.fusiontech.dtos.orderItem.OrderItemCreateDto;
import com.multishop.fusiontech.dtos.orderItem.OrderItemDto;
import com.multishop.fusiontech.dtos.orderItem.OrderItemUpdateDto;
import com.multishop.fusiontech.models.Order;
import com.multishop.fusiontech.models.OrderItem;
import com.multishop.fusiontech.repositories.OrderItemRepository;
import com.multishop.fusiontech.repositories.OrderRepository;
import com.multishop.fusiontech.services.OrderItemService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderItemDto getOrderItemById(Long id) {
        OrderItem repoOrderItem = orderItemRepository.findById(id).orElseThrow();
        OrderItemDto orderItem = modelMapper.map(repoOrderItem, OrderItemDto.class);
        return orderItem;
    }

    @Override
    public boolean createOrderItem(Long orderId, OrderItemCreateDto orderItemCreateDto) {
        try {
            Order findOrder = orderRepository.findById(orderId).orElseThrow();
            OrderItem newItem = modelMapper.map(orderItemCreateDto, OrderItem.class);
            newItem.setOrder(findOrder);
            orderItemRepository.save(newItem);
            return true;
        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    @Override
    public boolean updateOrderItem(Long id, OrderItemUpdateDto orderItemUpdateDto) {
        try {
            OrderItem findItem = orderItemRepository.findById(id).orElseThrow();
            findItem.setPrice(orderItemUpdateDto.getPrice());
            findItem.setQuantity(orderItemUpdateDto.getQuantity());
            orderItemRepository.save(findItem);
            return true;
        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    @Override
    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }
}
