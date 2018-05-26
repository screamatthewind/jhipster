package com.therealdanvega.service.mapper;

import com.therealdanvega.domain.*;
import com.therealdanvega.service.dto.JobHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity JobHistory and its DTO JobHistoryDTO.
 */
@Mapper(componentModel = "spring", uses = {DepartmentMapper.class, JobMapper.class, EmployeeMapper.class})
public interface JobHistoryMapper extends EntityMapper<JobHistoryDTO, JobHistory> {

    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "job.id", target = "jobId")
    @Mapping(source = "employee.id", target = "employeeId")
    JobHistoryDTO toDto(JobHistory jobHistory);

    @Mapping(source = "departmentId", target = "department")
    @Mapping(source = "jobId", target = "job")
    @Mapping(source = "employeeId", target = "employee")
    JobHistory toEntity(JobHistoryDTO jobHistoryDTO);

    default JobHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        JobHistory jobHistory = new JobHistory();
        jobHistory.setId(id);
        return jobHistory;
    }
}
