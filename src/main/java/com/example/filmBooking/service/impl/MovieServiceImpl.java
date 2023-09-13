package com.example.filmBooking.service.impl;

import com.example.filmBooking.model.Movie;
import com.example.filmBooking.repository.MovieRepository;
import com.example.filmBooking.service.MovieService;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository repository;

//    private ImportMovie importMovie = new ImportMovie();

    @Override
    public List<Movie> fillAll() {
        return repository.findAll();
    }

    @Override
    public List<Movie> showPhimDangChieu() {
        return repository.showPhimDangChieu();
    }

    @Override
    public List<Movie> showPhimSapChieu() {
        return repository.showPhimSapChieu();
    }
    @Override
    public Movie save(Movie movie) {
        Random generator = new Random();
        int value = generator.nextInt((100000 - 1) + 1) + 1;
        movie.setCode("mv" + value);
        java.util.Date date = new java.util.Date();
        if (date.after(movie.getEndDate())) {
            movie.setStatus("Đã chiếu");
        } else if (date.before(movie.getPremiereDate())) {
            movie.setStatus("Sắp chiếu");
        } else {
            movie.setStatus("Đang chiếu");
        }
        return repository.save(movie);
    }


    //tự động check ngày để đổi trạng thái phim
    @Scheduled(fixedRate = 86400000)
    public void scheduleFixedRate() {
        // check danh sách movie
        List<Movie> movies = repository.findAll();
        for (Movie dto : movies) {
            java.util.Date date = new java.util.Date();
            if (date.after(dto.getEndDate())) {
                dto.setStatus("Đã chiếu");
                repository.save(dto);
            } else if (date.before(dto.getPremiereDate())) {
                dto.setStatus("Sắp chiếu");
                repository.save(dto);
            } else {
                dto.setStatus("Đang chiếu");
                repository.save(dto);
            }
        }
    }

    @Override
    public Movie update(UUID id, Movie movie) {
        Movie movieNew = findById(id);
        movieNew.setName(movie.getName());
        movieNew.setMovieDuration(movie.getMovieDuration());
        movieNew.setTrailer(movie.getTrailer());
        movieNew.setPremiereDate(movie.getPremiereDate());
        movieNew.setEndDate(movie.getEndDate());
        movieNew.setStatus(movie.getStatus());
        movieNew.setEndDate(movie.getEndDate());
        movieNew.setDirector(movie.getDirector());
        movieNew.setPerformers(movie.getPerformers());
        movieNew.setLanguages(movie.getLanguages());
        movieNew.setImage(movie.getImage());
        movieNew.setMovieType(movie.getMovieType());
        movieNew.setDescription(movie.getDescription());
        return repository.save(movieNew);
    }

    @Override
    public Movie findById(UUID id) {
        return repository.findById(id).get();
    }

    @Override
    public void exportExcel() {

    }

    @Override
    public Movie readExcel() {
        try {
            FileInputStream excelFile = new FileInputStream(new File("C:\\Users\\dieul\\Desktop\\tai_lieu\\projectGraduate\\Demo-ApachePOI-Excel.xlsx"));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            DataFormatter fmt = new DataFormatter();
            Iterator<Row> iterator = datatypeSheet.iterator();
            Row firstRow = iterator.next();
//            Cell firstCell = firstRow.getCell(1);
//            System.out.println(firstCell.getStringCellValue());
            List<Movie> listMovie = new ArrayList<Movie>();
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Movie movie = new Movie();
                movie.setName(currentRow.getCell(1).getStringCellValue());
                movie.setMovieDuration(Integer.parseInt(fmt.formatCellValue(currentRow.getCell(2))));
                movie.setPremiereDate(new Date(String.valueOf(currentRow.getCell(3))));
                movie.setEndDate(new Date(String.valueOf(currentRow.getCell(4))));
                movie.setDirector(currentRow.getCell(5).getStringCellValue());
                movie.setPerformers(currentRow.getCell(6).getStringCellValue());
                movie.setLanguages(currentRow.getCell(7).getStringCellValue());
                movie.setImage(currentRow.getCell(8).getStringCellValue());
                movie.setMovieType(currentRow.getCell(9).getStringCellValue());
                movie.setDescription(currentRow.getCell(10).getStringCellValue());
                movie.setTrailer(currentRow.getCell(11).getStringCellValue());
                listMovie.add(movie);
            }
            for (Movie movie : listMovie) {
//                movie.setId();
                save(movie);
            }
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void delete(UUID id) {
        repository.delete(findById(id));
    }
}
