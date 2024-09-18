package com.multishop.fusiontech.services.impls;

import com.multishop.fusiontech.dtos.order.OrderPlaceDto;
import com.multishop.fusiontech.enums.OrderStatus;
import com.multishop.fusiontech.enums.PaymentStatus;
import com.multishop.fusiontech.models.Basket;
import com.multishop.fusiontech.models.Order;
import com.multishop.fusiontech.models.OrderItem;
import com.multishop.fusiontech.models.UserEntity;
import com.multishop.fusiontech.repositories.BasketRepository;
import com.multishop.fusiontech.repositories.OrderItemRepository;
import com.multishop.fusiontech.repositories.OrderRepository;
import com.multishop.fusiontech.repositories.UserRepository;
import com.multishop.fusiontech.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final BasketRepository basketRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(UserRepository userRepository, BasketRepository basketRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.basketRepository = basketRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean checkout(String userEmail, OrderPlaceDto orderPlaceDto) {
        try {
            UserEntity findUser = userRepository.findByEmail(userEmail);
            List<Basket> findUserBaskets = findUser.getBaskets();

            Order order = new Order();
            order.setUser(findUser);
            order.setOrderDate(new Date());

            order.setName(orderPlaceDto.getName());
            order.setSurname(orderPlaceDto.getSurname());
            order.setPhoneNumber(orderPlaceDto.getPhoneNumber());
            order.setEmail(orderPlaceDto.getEmail());
            order.setCity(orderPlaceDto.getCity());
            order.setAddress(orderPlaceDto.getAddress());
            order.setMessage(orderPlaceDto.getMessage());
            order.setDifferBilling(orderPlaceDto.isDifferBilling());

            if (!orderPlaceDto.isDifferBilling()) {
                order.setBillName(orderPlaceDto.getName());
                order.setBillSurname(orderPlaceDto.getSurname());
                order.setBillPhoneNumber(orderPlaceDto.getPhoneNumber());
                order.setBillEmail(orderPlaceDto.getEmail());
                order.setBillCity(orderPlaceDto.getCity());
                order.setBillAddress(orderPlaceDto.getAddress());
                order.setBillMessage(orderPlaceDto.getMessage());
            } else {
                order.setBillName(orderPlaceDto.getBillName());
                order.setBillSurname(orderPlaceDto.getBillSurname());
                order.setBillPhoneNumber(orderPlaceDto.getBillPhoneNumber());
                order.setBillEmail(orderPlaceDto.getBillEmail());
                order.setBillCity(orderPlaceDto.getBillCity());
                order.setBillAddress(orderPlaceDto.getBillAddress());
                order.setBillMessage(orderPlaceDto.getBillMessage());
            }

            order.setPaymentMethod(orderPlaceDto.getPaymentMethod());
            order.setOrderStatus(OrderStatus.PENDING);
            order.setPaymentStatus(PaymentStatus.PENDING);
            orderRepository.save(order);

            for (Basket findBasket : findUserBaskets) {
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(findBasket.getProduct());
                orderItem.setPrice(findBasket.getProduct().getPrice());
                orderItem.setQuantity(findBasket.getQuantity());
                orderItem.setOrder(order);
                orderItemRepository.save(orderItem);
            }

            basketRepository.deleteAll(findUserBaskets);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
