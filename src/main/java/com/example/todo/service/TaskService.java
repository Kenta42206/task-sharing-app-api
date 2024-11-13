package com.example.todo.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.todo.entity.Category;
import com.example.todo.entity.Status;
import com.example.todo.entity.Task;
import com.example.todo.entity.User;
import com.example.todo.exception.ResourceNotFoundException;
import com.example.todo.repository.CategoryRepository;
import com.example.todo.repository.StatusRepository;
import com.example.todo.repository.TaskRepository;
import com.example.todo.repository.UserRepository;

@Service
public class TaskService {

    @Autowired
    private final TaskRepository taskRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final StatusRepository statusRepository;

    @Autowired
    private final CategoryRepository categoryRepository;

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, StatusRepository statusRepository, CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.statusRepository = statusRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Task> getAllTasks() {
        logger.info("Fetching all tasks.");
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        logger.info("Fetching task with id: {}", id);
        return taskRepository.findById(id);
    }

    public List<Task> getTasksByUserId(Long userId) {
        logger.info("Fetching tasks for user with id: {}", userId);
        return taskRepository.findByUserId(userId);
    }

    public List<Task> getTasksByStatusId(Long statusId) {
        logger.info("Fetching tasks with status id: {}", statusId);
        return taskRepository.findByStatus_Id(statusId);
    }

    public List<Task> getTasksByCategoryId(Long categoryId) {
        logger.info("Fetching tasks with category id: {}", categoryId);
        return taskRepository.findByCategory_Id(categoryId);
    }

    public List<Task> getTasksByPriority(Integer priority) {
        logger.info("Fetching tasks with priority: {}", priority);
        return taskRepository.findByPriority(priority);
    }

    public List<Task> getTasksByRoomId(Long roomId){
        logger.info("Fetching tasks with room id: {}", roomId);
        return taskRepository.findByRoomId(roomId);
    }

    public Task createTask(Task task) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();

        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            logger.error("User not found: {}", username);
            throw new ResourceNotFoundException("User not found");
        });

        Category category = categoryRepository.findById(task.getCategory().getId())
            .orElseThrow(() -> {
                logger.error("Category not found with id: {}", task.getCategory().getId());
                throw new ResourceNotFoundException("Category not found");
            });
        Status status = statusRepository.findById(task.getStatus().getId())
            .orElseThrow(() -> {
                logger.error("Status not found with id: {}", task.getStatus().getId());
                throw new ResourceNotFoundException("Status not found");
            });

        task.setUser(user);
        task.setCategory(category);
        task.setStatus(status);
        
        Task savedTask = taskRepository.save(task);
        logger.info("Task created with id: {}", savedTask.getId());
        return savedTask;
    }

    public Task updateTask(Long id, Task taskDetails) {
        Task targetTask = taskRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("Task not found with id: {}", id);
                throw new ResourceNotFoundException("Task not found");
            });

        targetTask.setTitle(taskDetails.getTitle());
        targetTask.setDescription(taskDetails.getDescription());
        targetTask.setStatus(taskDetails.getStatus());
        targetTask.setCategory(taskDetails.getCategory());
        targetTask.setImportance(taskDetails.getImportance());
        targetTask.setProgress(taskDetails.getProgress());
        targetTask.setPriority(taskDetails.getPriority());
        targetTask.setDueDate(taskDetails.getDueDate());

        Task updatedTask = taskRepository.save(targetTask);
        logger.info("Task updated with id: {}", updatedTask.getId());
        return updatedTask;
    }

    @Transactional
    public void deleteTaskById(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            logger.info("Task deleted with id: {}", id);
        } else {
            logger.error("Task not found with id: {}", id);
            throw new ResourceNotFoundException("Task not found with id " + id);
        }
    }
}
