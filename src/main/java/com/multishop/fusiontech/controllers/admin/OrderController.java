package com.multishop.fusiontech.controllers.admin;

import com.multishop.fusiontech.dtos.order.OrderCreateDto;
import com.multishop.fusiontech.dtos.order.OrderDto;
import com.multishop.fusiontech.dtos.order.OrderUpdateDto;
import com.multishop.fusiontech.dtos.orderItem.OrderItemDto;
import com.multishop.fusiontech.dtos.orderItem.OrderItemUpdateDto;
import com.multishop.fusiontech.dtos.user.UserDto;
import com.multishop.fusiontech.enums.OrderStatus;
import com.multishop.fusiontech.enums.PaymentMethod;
import com.multishop.fusiontech.enums.PaymentStatus;
import com.multishop.fusiontech.payloads.PaginationPayload;
import com.multishop.fusiontech.services.OrderItemService;
import com.multishop.fusiontech.services.OrderService;
import com.multishop.fusiontech.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final UserService userService;

    public OrderController(OrderService orderService, OrderItemService orderItemService, UserService userService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
        this.userService = userService;
    }

    @GetMapping("/admin/order")
    public String showIndexPage(Model model, Principal principal, Integer currentPage) {
        PaginationPayload<OrderDto> orders = orderService.getAllOrders(currentPage);
        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("searchUrl", "/admin/search/order");

        UserDto user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);

        return "/admin/order/index";
    }

    @GetMapping("/admin/order/create")
    public String showCreatePage(Principal principal, Model model) {
        UserDto user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);
        return "/admin/order/create";
    }

    @PostMapping("/admin/order/create")
    public String createOrder(OrderCreateDto orderCreateDto, Principal principal) {
        boolean result = orderService.createOrder(principal.getName(), orderCreateDto);
        if (result) {
            return "redirect:/admin/order";
        }
        return "redirect:/admin/order/create";
    }

    @GetMapping("/admin/order/update/{id}")
    public String showUpdatePage(@PathVariable Long id, Model model, Principal principal) {
        OrderDto order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        model.addAttribute("paymentMethods", PaymentMethod.values());
        model.addAttribute("orderStatuses", OrderStatus.values());
        model.addAttribute("paymentStatuses", PaymentStatus.values());

        UserDto user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);

        return "/admin/order/update";
    }

    @PostMapping("/admin/order/update/{id}")
    public String updateOrder(OrderUpdateDto orderUpdateDto, @PathVariable Long id) {
        boolean result = orderService.updateOrder(id, orderUpdateDto);
        if (result) {
            return "redirect:/admin/order";
        } else {
            return "redirect:/admin/order/update/" + id;
        }
    }

    @GetMapping("/admin/order/update-item/{id}")
    public String showUpdateItemPage(@PathVariable Long id, Model model, Principal principal) {
        OrderItemDto orderItem = orderItemService.getOrderItemById(id);
        model.addAttribute("orderItem", orderItem);

        UserDto user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);

        return "/admin/order/update-item";
    }

    @PostMapping("/admin/order/update-item/{id}")
    public String updateOrderItem(OrderItemUpdateDto orderItemUpdateDto, @PathVariable Long id) {
        OrderDto order = orderService.getOrderByOrderItemId(id);
        boolean result = orderItemService.updateOrderItem(id, orderItemUpdateDto);
        if (result) {
            return "redirect:/admin/order/update/" + order.getId();
        } else {
            return "redirect:/admin/order/update-item/" + id;
        }
    }

    @GetMapping("/admin/order/delete/{id}")
    public String showDeletePage(@PathVariable Long id, Model model, Principal principal) {
        OrderDto order = orderService.getOrderById(id);
        model.addAttribute("order", order);

        UserDto user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);

        return "/admin/order/delete";
    }

    @PostMapping("/admin/order/delete/{id}")
    public String deleteOrder(@RequestParam Long orderId) {
        orderService.deleteOrder(orderId);
        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/delete-item/{id}")
    public String showItemDeletePage(@PathVariable Long id, Model model, Principal principal) {
        OrderItemDto orderItem = orderItemService.getOrderItemById(id);
        model.addAttribute("orderItem", orderItem);

        UserDto user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);

        return "/admin/order/delete-item";
    }

    @PostMapping("/admin/order/delete-item/{id}")
    public String deleteOrderItem(@RequestParam Long orderItemId) {
        OrderDto order = orderService.getOrderByOrderItemId(orderItemId);
        orderItemService.deleteOrderItem(orderItemId);
        return "redirect:/admin/order/update/" + order.getId();
    }
}
