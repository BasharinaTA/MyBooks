package com.mybooks.repositories;

import com.mybooks.model.entities.Book;
import com.mybooks.model.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Optional<Book> findByIdAndProfile(Integer id, Profile profile);

    @Query("""
             from Book b where b.profile = :profile
             and b.dateStart is not null
             and extract(year from b.dateStart) = :year
             order by b.dateStart, b.dateFinish, b.id
            """)
    List<Book> findReadingBooksByYear(Profile profile, Integer year);

    @Query("""
             from Book b where b.profile = :profile
             and b.dateStart is null
             order by b.created desc
            """)
    List<Book> findPlannedBooks(Profile profile);

    @Query("""
             from Book b where  b.profile = :profile
             and b.dateStart is not null and b.dateStart <= current_timestamp
             and b.dateFinish is null
             order by b.dateStart desc, b.id
            """)
    List<Book> findNotReadBooks(Profile profile);

    @Query("""
             from Book b where b.profile = :profile
             and (b.dateStart is null and b.dateFinish is not null
             or b.dateStart > current_timestamp
             or b.dateFinish > current_timestamp
             or b.dateStart > b.dateFinish)
             order by b.dateStart desc nulls last, b.dateFinish, b.id
            """)
    List<Book> findBooksWithIncorrectDates(Profile profile);

    @Query("""
            from Book b where b.profile =:profile
            and  b.dateStart is not null and b.dateStart <= current_timestamp
            and b.dateFinish is not null and b.dateFinish <= current_timestamp
            and b.dateStart <= b.dateFinish
            order by b.dateStart, b.dateFinish, b.id
            """)
    List<Book> findReadBooksWithCorrectDates(Profile profile);

}
