package ca.ailurus.dashboard.managers;

import ca.ailurus.dashboard.entities.UseCase;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;

@Singleton
public class UseCaseManagerImpl implements UseCaseManager {
    static UseCase[] useCases = {
        new UseCase("blog", "Blog", " to share your thoughts", UseCase.Types.Personal),
        new UseCase("profile", "Profile Page", " to showcase your work", UseCase.Types.Personal),
        new UseCase("game-server", "Game Server", " to host games for your friends", UseCase.Types.Personal),
        new UseCase("corporate-website", "Corporate Website", " to showcase your company", UseCase.Types.Business),
        new UseCase("web-server", "Web Server", " to run your own website", UseCase.Types.Programming),
        new UseCase("source-control", "Source Control", " to safely store your source code", UseCase.Types.Programming)
    };

    @Override
    public List<UseCase> getAll() {
        return Arrays.asList(useCases);
    }

    @Override
    public UseCase find(String useCaseName) {
        for (UseCase useCase: useCases) {
            if (useCase.name.equals(useCaseName)) {
                return useCase;
            }
        }
        return null;
    }
}
