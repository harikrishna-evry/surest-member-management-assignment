package com.surest.member.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testHandleMemberNotFoundException() {
        MemberNotFoundException ex = new MemberNotFoundException("Member not found");

        ResponseEntity<String> response = handler.handleMemberNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Member not found", response.getBody());
    }

    @Test
    void testHandleMemberAlreadyExistsException() {
        MemberAlreadyExistsException ex = new MemberAlreadyExistsException("Member already exists");

        ResponseEntity<String> response = handler.handleMemberAlreadyExistsException(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Member already exists", response.getBody());
    }

    @Test
    void testHandleMethodArgumentNotValidException() {

        BindingResult bindingResult = mock(BindingResult.class);

        ObjectError error1 = new ObjectError("field1", "First name is required");
        error1.wrap(null);
        ObjectError error2 = new ObjectError("field2", "Last name is required");
        error2.wrap(null);

        when(bindingResult.getAllErrors()).thenReturn(List.of(error1, error2));

        MethodArgumentNotValidException ex =
                new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<?> response = handler.handleMethodArgumentNotValidException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof java.util.Map);

        var map = (java.util.Map<String, String>) response.getBody();

        assertEquals(2, map.size());
        assertTrue(map.containsKey("First name is required"));
        assertTrue(map.containsKey("Last name is required"));
    }
}
