package org.shedenys.timestamps.model.file.factory;

import org.shedenys.timestamps.model.file.entity.File;

import java.io.InputStream;
import java.nio.file.Path;

public interface FileFactoryInterface {

    File create(Path path, InputStream inputStream) throws Exception;
}
