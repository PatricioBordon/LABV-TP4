package logic;

import java.util.List;
import entity.Optional;
import entity.Especialidad;

public interface IEspecialidadLogic {

	void add(Especialidad e);

	Optional<Especialidad> findById(int id);

	void update(Especialidad e);

	void disable(Especialidad e);

	void enable(Especialidad e);

	void permanentlyDelete(Especialidad e);

	List<Especialidad> list(int page, int size);

	List<Especialidad> list();

}