package com.system.volleyball.Pojo;

import com.system.volleyball.entity.Booking;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingPojo {
    private Integer bookId;
    private Integer fid;
    private Integer id;
    private String date;
    private String starting;
    private String email;



    public BookingPojo(Booking booking) {
        this.bookId= booking.getBookId();
        this.fid= booking.getVolleyball().getFut_salId();
        this.id= booking.getUser().getId();
        this.date= String.valueOf(booking.getDate());
        this.starting= booking.getStarting();
        this.email = booking.getEmail();

    }
}

