package daoImpl;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Query;

import dao.IMedicoDAO;
import daoImpl.DataManager.ContainerFor;
import entity.Medico;

public class MedicoDAOImpl implements IMedicoDAO {
    
    @Override
    public void add(Medico medico) {
        DataManager.transact(session -> {
            session.save(medico);
        });
    }
    
    @Override
    public Medico getById(int id) {
        final ContainerFor<Medico> cfMedico = new ContainerFor<>(null);
        DataManager.run(session -> {
            String hql = "FROM Medico WHERE id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            cfMedico.object = (Medico) query.uniqueResult();
        });
        return cfMedico.object;
    }
    
    @Override
    public List<Medico> list() {
        return list(1, 15);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<Medico> list(int page, int size) {
        final ContainerFor<List<Medico>> cfList = new ContainerFor<>(null);
        DataManager.run(session -> {
            String hql = "FROM Medico";
            Query query = session.createQuery(hql);
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);
            cfList.object = query.list();
        });
        return cfList.object;
    }
    
    @Override
    public void update(Medico medico) {
        DataManager.transact(session -> {
            session.update(medico);
        });
    }
    
    @Override
    public void erase(Medico medico) {
        DataManager.transact(session -> {
            session.delete(medico);
        });
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> listMedicosLegajoAscP2() {
		final ContainerFor<List<Object[]>> cfList = new ContainerFor<>(null);
        DataManager.run(session -> {
            String hql = "SELECT m.legajo, m.nombre, m.apellido FROM Medico m ORDER BY m.legajo ASC";
            Query query = session.createQuery(hql);
            cfList.object = query.list();
        });
        return cfList.object;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getTurnosMedicoEnFecha(int legajo, LocalDate fecha) {
        final ContainerFor<List<Object[]>> cfList = new ContainerFor<>(null);
        DataManager.run(session -> {
            String hql = "SELECT m.legajo, t.fechaAlta, t.estado " +
                         "FROM Medico m INNER JOIN m.turnos t " +
                         "WHERE m.legajo = :legajo AND t.fechaAlta = :fecha";
            Query query = session.createQuery(hql);
            query.setParameter("legajo", legajo);
            query.setParameter("fecha", java.sql.Date.valueOf(fecha));
            cfList.object = query.list();
        });
        return cfList.object;
    }
}
