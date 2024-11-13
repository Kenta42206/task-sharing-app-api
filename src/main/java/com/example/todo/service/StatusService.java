package com.example.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo.entity.Status;
import com.example.todo.repository.StatusRepository;

@Service
public class StatusService {

    @Autowired
    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public List<Status> getAllStatuses() {
        return statusRepository.findAll();
    }

    public Optional<Status> getStatusById(Long id) {
        return statusRepository.findById(id);
    }

    public Status createStatus(Status status) {
        return statusRepository.save(status);
    }

    public Status updateStatus(Long id, Status statusDetails) {
        Status targetStatus = statusRepository.findById(id).orElseThrow(() -> new RuntimeException("Status not found"));
        targetStatus.setName(statusDetails.getName());
        targetStatus.setDescription(statusDetails.getDescription());
        return statusRepository.save(targetStatus);
    }

    public void deleteStatus(Long id) {
        statusRepository.deleteById(id);
    }
}