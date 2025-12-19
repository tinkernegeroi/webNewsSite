package com.javaLab.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO для передачи серверного времени.
 * Используется в UserController.time().
 */
@Data
@AllArgsConstructor
public class ServerTimeDTO {

    /**
     * Текущее серверное время в формате ISO или другом.
     */
    private String serverTime;
}
