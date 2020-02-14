package com.converter.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Calendar;

@Entity
@Table(name = "conversion")
@NoArgsConstructor
@Data
@ToString(exclude = {"user"})
public class Conversion {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "entered_value")
    @NotNull(message = "Enter the value")
    private Float enteredValue;

    @Column(name = "first_char_code")
    private String firstCharCode;

    @Column(name = "second_char_code")
    private String secondCharCode;

    @Column(name = "result")
    private Float result;

    @Column(name = "date")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private User user;

    @PrePersist
    public void currentDate() {
        this.date = new Date(Calendar.getInstance().getTime().getTime());
    }

}
