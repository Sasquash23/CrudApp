package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;


import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskMapper taskMapper;

    @MockBean
    private DbService dbService;


    @Test
    public void shouldFetchEmptyTask() throws Exception {
        //Given
        List<TaskDto> taskDtoList = new ArrayList<>();
        when(taskMapper.mapToTaskDtoList(anyList())).thenReturn(taskDtoList);

        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldFetchTasksAll() throws Exception {
        //Given
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskDtoList.add(new TaskDto(1L, "Ricky", "Get schwifty"));
        taskDtoList.add(new TaskDto(2L, "Morty", "Get schwiftyyyyyyyyyyyyyyyyyyyyy"));

        when(taskMapper.mapToTaskDtoList(dbService.getAllTasks())).thenReturn(taskDtoList);

        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Ricky")))
                .andExpect(jsonPath("$[0].content", is("Get schwifty")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Morty")))
                .andExpect(jsonPath("$[1].content", is("Get schwiftyyyyyyyyyyyyyyyyyyyyy")));
    }

    @Test
    public void shouldFetchTaskById() throws Exception {
        // Given
        Task task = new Task(1L, "Ricky", "Get schwifty");
        TaskDto taskDto = new TaskDto(1L, "Ricky", "Get schwifty");

        when(dbService.getTask(anyLong())).thenReturn(Optional.ofNullable(task));
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        // When & Then
        mockMvc.perform(get("/v1/task/getTask").param("id", "1")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Ricky")))
                .andExpect(jsonPath("$.content", is("Get schwifty")));
    }

    @Test
    public void shouldUpdateTask()throws Exception{
        // Given
        TaskDto taskDto = new TaskDto(1L, "Ricky", "Get schwifty");
        TaskDto updatedTaskDto = new TaskDto(1L, "Ricky 23", "I'm a bit of a stickler Meeseeks");

        when(taskMapper.mapToTaskDto(dbService.saveTask(taskMapper.mapToTask(taskDto)))).thenReturn(updatedTaskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        // When &  Then
        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Ricky 23")))
                .andExpect(jsonPath("$.content", is("I'm a bit of a stickler Meeseeks")));
    }

    @Test
    public void shouldCreateTask() throws Exception {
        //Given
        Task task = new Task(1L, "Ricky", "Get schwifty");
        TaskDto taskDto = new TaskDto(1L, "Ricky", "Get schwifty");

        when(dbService.saveTask(task)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When and Then
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        //Given
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskDtoList.add(new TaskDto(1L, "Ricky", "Get schwifty"));

        when(taskMapper.mapToTaskDtoList(anyList())).thenReturn(taskDtoList);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDtoList);

        //When and Then
        mockMvc.perform(delete("/v1/task/deleteTask").param("id", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}