/*
 * @ {#} TaskDto.java   1.0     8/8/2025
 *
 * Copyright (c) 2025 IUH. All rights reserved.
 */

package vn.edu.iuh.fit.backend.dtos;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

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
public class TaskDto implements Serializable {
    private Long id;
    private String title;
    private String description;
    private String status;
    private LocalDate deadline;
    private List<ChecklistDto> checklist;


}

