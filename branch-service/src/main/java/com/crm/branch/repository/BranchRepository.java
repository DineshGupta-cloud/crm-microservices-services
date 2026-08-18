package com.crm.branch.repository;
import com.crm.branch.entity.Branch; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface BranchRepository extends JpaRepository<Branch,Long>{ List<Branch> findByCompanyId(Long companyId); boolean existsByCode(String code); }