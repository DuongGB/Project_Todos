/*
 * @ {#} ChecklistItem.java   1.0     8/8/2025
 *
 * Copyright (c) 2025 IUH. All rights reserved.
 */

package vn.edu.iuh.fit.backend.models;

import jakarta.persistence.*;
import lombok.*;

/*
 * @description:
 * @author: Nguyen Tan Thai Duong
 * @date:   8/8/2025
 * @version:    1.0
 */
@Entity
@Table(name = "checklist_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChecklistItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private boolean checked;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
}

