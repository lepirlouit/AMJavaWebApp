package be.pir.am.dao;

import be.pir.am.api.dao.AthleteDao;
import be.pir.am.entities.AthleteEntity;
import be.pir.am.entities.AthleteEntity_;
import be.pir.am.entities.LicenseEntity;
import be.pir.am.entities.LicenseEntity_;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Stateless
public class AthleteDaoImpl extends AbstractEntityDao<AthleteEntity> implements AthleteDao {

    public AthleteDaoImpl() {
        super(AthleteEntity.class);
    }

    @Override
    public List<LicenseEntity> findAthleteByBibGenderAndBirthdayMinMax(String bib, Character gender, Date dateMin,
                                                                       Date dateMax) {
        List<Character> genders;
        if (gender == null) {
            genders = Arrays.asList('W', 'F', 'M', 'H');
        } else if (Character.valueOf('W').equals(gender) || Character.valueOf('F').equals(gender)) {
            genders = Arrays.asList('W', 'F');
        } else if (Character.valueOf('M').equals(gender) || Character.valueOf('H').equals(gender)) {
            genders = Arrays.asList('M', 'H');
        } else {
            genders = Collections.singletonList(gender);
        }


        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<LicenseEntity> cq = cb.createQuery(LicenseEntity.class);
        Root<LicenseEntity> license = cq.from(LicenseEntity.class);
        Join<LicenseEntity, AthleteEntity> athlete = license.join(LicenseEntity_.athlete, JoinType.LEFT);
//		cq.select(license.get("athlete"));
        cq.where(cb.between(athlete.get(AthleteEntity_.birthdate), dateMin, dateMax), athlete.get(AthleteEntity_.gender).in(genders),
                cb.equal(license.get("bib"), bib));
        return em.createQuery(cq).getResultList();
    }

}
