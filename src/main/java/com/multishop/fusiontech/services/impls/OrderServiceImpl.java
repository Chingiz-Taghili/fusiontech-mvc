package com.multishop.fusiontech.services.impls;

import com.multishop.fusiontech.dtos.order.OrderCreateDto;
import com.multishop.fusiontech.dtos.order.OrderDto;
import com.multishop.fusiontech.dtos.order.OrderUpdateDto;
import com.multishop.fusiontech.enums.OrderStatus;
import com.multishop.fusiontech.enums.PaymentStatus;
import com.multishop.fusiontech.models.CartItem;
import com.multishop.fusiontech.models.Order;
import com.multishop.fusiontech.models.OrderItem;
import com.multishop.fusiontech.models.UserEntity;
import com.multishop.fusiontech.payloads.PaginationPayload;
import com.multishop.fusiontech.repositories.CartItemRepository;
import com.multishop.fusiontech.repositories.OrderItemRepository;
import com.multishop.fusiontech.repositories.OrderRepository;
import com.multishop.fusiontech.repositories.UserRepository;
import com.multishop.fusiontech.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(UserRepository userRepository, CartItemRepository cartItemRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean createOrder(String userEmail, OrderCreateDto orderCreateDto) {
        try {
            UserEntity findUser = userRepository.findByEmail(userEmail);

            Order order = new Order();
            order.setUser(findUser);
            order.setOrderDate(LocalDateTime.now());

            order.setName(orderCreateDto.getName());
            order.setSurname(orderCreateDto.getSurname());
            order.setPhoneNumber(orderCreateDto.getPhoneNumber());
            order.setEmail(orderCreateDto.getEmail());
            order.setCity(orderCreateDto.getCity());
            order.setAddress(orderCreateDto.getAddress());
            order.setMessage(orderCreateDto.getMessage());
            order.setDifferBilling(orderCreateDto.isDifferBilling());

            if (!orderCreateDto.isDifferBilling()) {
                order.setBillName(orderCreateDto.getName());
                order.setBillSurname(orderCreateDto.getSurname());
                order.setBillPhoneNumber(orderCreateDto.getPhoneNumber());
                order.setBillEmail(orderCreateDto.getEmail());
                order.setBillCity(orderCreateDto.getCity());
                order.setBillAddress(orderCreateDto.getAddress());
                order.setBillMessage(orderCreateDto.getMessage());
            } else {
                order.setBillName(orderCreateDto.getBillName());
                order.setBillSurname(orderCreateDto.getBillSurname());
                order.setBillPhoneNumber(orderCreateDto.getBillPhoneNumber());
                order.setBillEmail(orderCreateDto.getBillEmail());
                order.setBillCity(orderCreateDto.getBillCity());
                order.setBillAddress(orderCreateDto.getBillAddress());
                order.setBillMessage(orderCreateDto.getBillMessage());
            }

            order.setPaymentMethod(orderCreateDto.getPaymentMethod());
            order.setOrderStatus(OrderStatus.PENDING);
            order.setPaymentStatus(PaymentStatus.PENDING);
            orderRepository.save(order);

            List<CartItem> findUserItems = findUser.getCartItems();
            for (CartItem findItem : findUserItems) {
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(findItem.getProduct());
                orderItem.setPrice(findItem.getProduct().getDiscountPrice());
                orderItem.setQuantity(findItem.getQuantity());
                orderItem.setOrder(order);
                orderItemRepository.save(orderItem);
            }

            cartItemRepository.deleteAll(findUserItems);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateOrder(Long id, OrderUpdateDto orderUpdateDto) {
        try {
            Order findOrder = orderRepository.findById(id).orElseThrow();

            findOrder.setName(orderUpdateDto.getName());
            findOrder.setSurname(orderUpdateDto.getSurname());
            findOrder.setPhoneNumber(orderUpdateDto.getPhoneNumber());
            findOrder.setEmail(orderUpdateDto.getEmail());
            findOrder.setCity(orderUpdateDto.getCity());
            findOrder.setAddress(orderUpdateDto.getAddress());
            findOrder.setMessage(orderUpdateDto.getMessage());
            findOrder.setDifferBilling(orderUpdateDto.isDifferBilling());

            if (!orderUpdateDto.isDifferBilling()) {
                findOrder.setBillName(orderUpdateDto.getName());
                findOrder.setBillSurname(orderUpdateDto.getSurname());
                findOrder.setBillPhoneNumber(orderUpdateDto.getPhoneNumber());
                findOrder.setBillEmail(orderUpdateDto.getEmail());
                findOrder.setBillCity(orderUpdateDto.getCity());
                findOrder.setBillAddress(orderUpdateDto.getAddress());
                findOrder.setBillMessage(orderUpdateDto.getMessage());
            } else {
                findOrder.setBillName(orderUpdateDto.getBillName());
                findOrder.setBillSurname(orderUpdateDto.getBillSurname());
                findOrder.setBillPhoneNumber(orderUpdateDto.getBillPhoneNumber());
                findOrder.setBillEmail(orderUpdateDto.getBillEmail());
                findOrder.setBillCity(orderUpdateDto.getBillCity());
                findOrder.setBillAddress(orderUpdateDto.getBillAddress());
                findOrder.setBillMessage(orderUpdateDto.getBillMessage());
            }

            findOrder.setPaymentMethod(orderUpdateDto.getPaymentMethod());
            findOrder.setOrderStatus(orderUpdateDto.getOrderStatus());
            findOrder.setPaymentStatus(orderUpdateDto.getPaymentStatus());

            orderRepository.save(findOrder);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public OrderDto getOrderById(Long id) {
        Order repoOrder = orderRepository.findById(id).orElseThrow();
        OrderDto order = modelMapper.map(repoOrder, OrderDto.class);
        return order;
    }

    @Override
    public OrderDto getOrderByOrderItemId(Long itemId) {
        OrderItem item = orderItemRepository.findById(itemId).orElseThrow();
        Order repoOrder = orderRepository.findById(item.getOrder().getId()).orElseThrow();
        OrderDto order = modelMapper.map(repoOrder, OrderDto.class);
        return order;
    }

    @Override
    public PaginationPayload<OrderDto> getAllOrders(Integer pageNumber) {
        pageNumber = (pageNumber == null || pageNumber < 1) ? 1 : pageNumber;
        Pageable pageable = PageRequest.of(pageNumber -1, 10, Sort.by("id"));
        Page<Order> repoOrders = orderRepository.findAll(pageable);

        List<OrderDto> orders = repoOrders.getContent().stream().map(order -> modelMapper.map(order, OrderDto.class)).toList();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        for (int i = 0; i < orders.size(); i++) {
            if (repoOrders.getContent().get(i).getOrderDate() != null) {
                orders.get(i).setFormattedOrderDate(repoOrders.getContent().get(i).getOrderDate().format(formatter));
            }
        }

        PaginationPayload<OrderDto> paginationOrders = new PaginationPayload<>(repoOrders.getTotalPages(), pageNumber, orders);
        return paginationOrders;
    }

    @Override
    public List<OrderDto> getSearchOrders(String keyword) {
        List<Order> repoOrders = orderRepository.findByKeywordInColumnsIgnoreCase(keyword);
        List<OrderDto> orders = repoOrders.stream().map(order -> modelMapper.map(order, OrderDto.class)).toList();
        return orders;
    }
}
