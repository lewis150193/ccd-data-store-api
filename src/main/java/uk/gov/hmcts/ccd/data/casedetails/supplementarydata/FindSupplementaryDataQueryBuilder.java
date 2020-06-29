package uk.gov.hmcts.ccd.data.casedetails.supplementarydata;

import com.google.common.collect.Lists;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("find")
public class FindSupplementaryDataQueryBuilder implements SupplementaryDataQueryBuilder {

    private static final String SUPPLEMENTARY_DATA_QUERY = "SELECT cd.supplementary_data FROM case_data cd WHERE cd.reference = :reference";

    @Override
    public List<Query> buildQueries(EntityManager entityManager, String caseReference, Map<String, Object> requestData) {
        Query selectQuery = entityManager.createNativeQuery(SUPPLEMENTARY_DATA_QUERY);
        selectQuery.setParameter("reference", caseReference);
        selectQuery.unwrap(org.hibernate.query.NativeQuery.class)
            .addScalar("supplementary_data", JsonNodeBinaryType.INSTANCE);
        return Lists.newArrayList(selectQuery);
    }

    @Override
    public Operation operationType() {
        return Operation.FIND;
    }
}