package slowinski.radoslaw.gymlogger.workout.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import slowinski.radoslaw.gymlogger.workout.entity.TrainingLog;
import slowinski.radoslaw.gymlogger.workout.exception.WorkoutNotFoundException;
import slowinski.radoslaw.gymlogger.workout.model.response.TrainingLogResponse;
import slowinski.radoslaw.gymlogger.workout.repository.TrainingLogRepository;
import slowinski.radoslaw.gymlogger.workout.service.TrainingLogService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TrainingLogServiceImpl implements TrainingLogService {

    @Autowired
    TrainingLogRepository trainingLogRepository;
    @Autowired
    ConversionService conversionService;

    @Override
    public void update(TrainingLog trainingLog) {
        trainingLogRepository.save(trainingLog);
    }

    @Override
    public TrainingLogResponse createTrainingLog(LocalDate trainingDate) {
        TrainingLog trainingLog = new TrainingLog();
        trainingLog.setTrainingDate(trainingDate);
        trainingLogRepository.save(trainingLog);

        return conversionService.convert(trainingLog, TrainingLogResponse.class);
    }

    @Override
    public List<TrainingLog> getTrainingLogs() {
        return trainingLogRepository.findAll();
    }

    @Override
    public TrainingLogResponse getTrainingLog(Long trainingId) {
        Optional<TrainingLog> log = Optional.ofNullable(trainingLogRepository.findOne(trainingId));

        return conversionService.convert(log.orElseThrow(
                () -> new WorkoutNotFoundException("could not find training log with id#" + trainingId)),
                TrainingLogResponse.class);
    }
}
