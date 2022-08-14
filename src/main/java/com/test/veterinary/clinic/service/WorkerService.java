package com.test.veterinary.clinic.service;

import com.test.veterinary.clinic.model.Worker;
import com.test.veterinary.clinic.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    private final WorkerRepository workerRepository;

    @Autowired
    public WorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    public Boolean delete(Long workerId) {
        workerRepository.deleteById(workerId);

        return true;
    }

    public List<Worker> findAll() {
        return workerRepository.findAll();
    }

    public Optional<Worker> findById(Long workerId) {
        return workerRepository.findById(workerId);
    }


    public Worker save(Worker worker) {
        return workerRepository.save(worker);
    }

    public Worker update(Long workerId, Worker worker) {
        Optional<Worker> findWorker = workerRepository.findById(workerId);

        if (findWorker.isEmpty() || workerId != worker.getId()) {
            return null;
        }

        return workerRepository.save(worker);
    }

    public Boolean workerDocumentNumberExist(Worker worker) {
        Optional<Worker> findWorker = workerRepository.findByDocumentNumber(worker.getId() == null ? -1 : worker.getId(), worker.getDocumentNumber());

        return !findWorker.isEmpty();
    }
}
