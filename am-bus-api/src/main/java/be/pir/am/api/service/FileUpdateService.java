package be.pir.am.api.service;

import javax.ejb.Local;
import java.io.File;

/**
 * Created by Benoit on 14-05-16.
 */
@Local
public interface FileUpdateService {
    boolean updateFromWebBasicAuth(String url, String login, String password);

    boolean updateAthletes(File file);
}
