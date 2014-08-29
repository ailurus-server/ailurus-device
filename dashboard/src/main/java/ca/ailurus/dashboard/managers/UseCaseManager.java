package ca.ailurus.dashboard.managers;

import ca.ailurus.dashboard.entities.UseCase;
import com.google.inject.ImplementedBy;

import java.util.List;

@ImplementedBy(UseCaseManagerImpl.class)
public interface UseCaseManager {
    List<UseCase> getAll();

    UseCase find(String useCaseName);
}
