/*
 * @ {#} ChecklistDto.java   1.0     8/8/2025
 *
 * Copyright (c) 2025 IUH. All rights reserved.
 */

package vn.edu.iuh.fit.backend.dtos.response;

import lombok.*;

import java.io.Serializable;

/*
 * @description:
 * @author: Nguyen Tan Thai Duong
 * @date:   8/8/2025
 * @version:    1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChecklistDto implements Serializable {
    private Long id;
    private String content;
    private boolean checked;
}

