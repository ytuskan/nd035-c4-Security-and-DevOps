package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {

    private CartController cartController;

    private CartRepository cartRepository = mock(CartRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setup(){
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);
    }

    @Test
    public void addToCartHappyPath(){
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("asd");
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setQuantity(2);
        User user = new User();
        user.setUsername("asd");
        user.setCart(new Cart());
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        Item item = new Item();
        item.setId(1L);
        item.setPrice(BigDecimal.valueOf(10));
        when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));
        Cart cart = user.getCart();
        cart.addItem(item);
        when(cartRepository.save(cart)).thenReturn(cart);
        ResponseEntity<Cart> response = ResponseEntity.ok(cart);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void addToCartSadPath(){
        ResponseEntity<Cart> response = cartController.addTocart(new ModifyCartRequest());
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void removeFromCartHappyPath(){
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("asd");
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setQuantity(2);
        User user = new User();
        user.setUsername("asd");
        user.setCart(new Cart());
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        Item item = new Item();
        item.setId(1L);
        item.setPrice(BigDecimal.valueOf(10));
        when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));
        Cart cart = user.getCart();
        cart.removeItem(item);
        when(cartRepository.save(cart)).thenReturn(cart);
        ResponseEntity<Cart> response = ResponseEntity.ok(cart);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void removeFromCartSadPath(){
        ResponseEntity<Cart> response = cartController.removeFromcart(new ModifyCartRequest());
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
