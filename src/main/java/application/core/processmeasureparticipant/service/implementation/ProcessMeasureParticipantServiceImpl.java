package application.core.processmeasureparticipant.service.implementation;

import application.core.processmeasureparticipant.dao.ProcessMeasureParticipantDao;
import application.core.processmeasureparticipant.service.ProcessMeasureParticipantService;
import application.model.ProcessMeasureId;
import application.model.ProcessMeasureParticipant;
import application.model.ProcessMeasureParticipantId;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by alexiaborchgrevink on 5/17/18.
 */
@Transactional
@Service
public class ProcessMeasureParticipantServiceImpl implements ProcessMeasureParticipantService {

    @Autowired
    private ProcessMeasureParticipantDao processMeasureParticipantDao;

    @Override
    public ProcessMeasureParticipant findById(ProcessMeasureParticipantId id) {
        return this.processMeasureParticipantDao.findById(id);
    }

    @Override
    public List<ProcessMeasureParticipant> getAllProcessMeasureParticipants() {
        return this.processMeasureParticipantDao.getAllProcessMeasureParticipants();
    }

    @Override
    public void insert(ProcessMeasureParticipant processMeasureParticipant) throws HibernateException {
        this.processMeasureParticipantDao.insert(processMeasureParticipant);
    }

    @Override
    public void delete(ProcessMeasureParticipant processMeasureParticipant) throws HibernateException {
        this.processMeasureParticipantDao.delete(processMeasureParticipant);
    }

    @Override
    public List<ProcessMeasureParticipant> getParticipantsByProcessMeasure(ProcessMeasureId processMeasureId){
        return this.processMeasureParticipantDao.getParticipantByProcessMeasure(processMeasureId);
    }

    @Override
    public Integer countParticipantsByProcessMeasure(ProcessMeasureId processMeasureId) {
        return this.processMeasureParticipantDao.countParticipantsByProcessMeasure(processMeasureId);
    }
}
