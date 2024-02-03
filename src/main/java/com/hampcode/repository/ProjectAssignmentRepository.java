package com.hampcode.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import com.hampcode.model.ProjectAssignment;

import org.springframework.data.repository.query.Param;


import java.time.LocalDate;
import java.util.List;

public interface ProjectAssignmentRepository extends JpaRepository<ProjectAssignment, Long> {

    // Consulta derivada para encontrar asignaciones de proyecto en un rango de fechas
    List<ProjectAssignment> findByAssignedDateBetween(LocalDate startDate, LocalDate endDate);

    // Consulta JPQL para encontrar asignaciones de proyecto en un rango de fechas
    @Query("SELECT pa FROM ProjectAssignment pa WHERE pa.assignedDate BETWEEN :startDate AND :endDate")
    List<ProjectAssignment> findAssignmentsWithinDateRange(LocalDate startDate, LocalDate endDate);

    // Consulta SQL nativa para encontrar asignaciones de proyecto en un rango de fechas
    @Query(value = "SELECT * FROM project_assignments WHERE assigned_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<ProjectAssignment> findAssignmentsWithinDateRangeNative(LocalDate startDate, LocalDate endDate);


    @Query("SELECT COUNT(pa) > 0 FROM ProjectAssignment pa WHERE pa.id.projectId = :projectId AND pa.id.userId = :userId")
    boolean existsById_ProjectIdAndId_UserId(@Param("projectId") Long projectId, @Param("userId") Long userId);

    //TODO: procedure



}
