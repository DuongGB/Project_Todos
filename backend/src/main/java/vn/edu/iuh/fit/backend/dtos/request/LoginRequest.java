/*
 * @ {#} LoginRequest.java   1.0     8/14/2025
 *
 * Copyright (c) 2025 IUH. All rights reserved.
 */

package vn.edu.iuh.fit.backend.dtos.request;

import lombok.Data;

/*
 * @description:
 * @author: Nguyen Tan Thai Duong
 * @date:   8/14/2025
 * @version:    1.0
 */
@Data
public class LoginRequest {
    private String username;
    private String password;
}

