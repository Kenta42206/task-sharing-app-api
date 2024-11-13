package com.example.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo.entity.Lock;
import com.example.todo.repository.LockRepository;

import jakarta.transaction.Transactional;

@Service
public class LockService {
    @Autowired
    private LockRepository lockRepository;

    public List<Lock> getAllLocks(){
        return lockRepository.findAll();
    }

    public boolean isLocked(Long taskId, Long userId){
        Optional<Lock> lock = lockRepository.findByTaskId(taskId);

        //ロックが存在しない場合はfalseを返す
        if(!lock.isPresent()){
            return false;
        }

        //自分がロックをかけていたらfalseを返す
        else if(lock.get().getUserId().equals(userId)){
            return false;
        }
        
        return true;
    }

    @Transactional
    public boolean lockTask(Long taskId, Long userId){
        if(isLocked(taskId,userId)){
            return false;
        }
        lockRepository.save(new Lock(taskId, userId));
        return true;
    }

    @Transactional
    public void unlockedTask(Long taskId){
        lockRepository.deleteByTaskId(taskId);
    }

    public Long getLockOwner(Long taskId){
        return lockRepository.findByTaskId(taskId).map(Lock::getUserId).orElse(null);
    }
}
