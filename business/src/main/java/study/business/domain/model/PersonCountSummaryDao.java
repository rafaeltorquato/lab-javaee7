package study.business.domain.model;

public interface PersonCountSummaryDao {

    PersonCountSummary save(PersonCountSummary personCountSummary);

    PersonCountSummary findById(Long id);

}