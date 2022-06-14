package pl.edu.agh.DroneRadar.parser;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.nio.file.*;
import java.security.InvalidParameterException;

@Service
@PropertySource("classpath:path.properties")
public class DirectoryWatcher {
    private final String directoryPath;
    private final Parser parser;

    public DirectoryWatcher(Parser parser, Environment environment) {
        this.parser = parser;
        var prop = environment.getProperty("path.path");
        if (prop == null) throw new InvalidParameterException("Missing path.path variable in path.properties");
        this.directoryPath = prop;
        new Thread(() -> {
            try {
                watchDirectory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void watchDirectory() throws Exception {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get(directoryPath);
        path.register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE);

        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent<Path> ev = cast(event);
                if(ev.context() == null) continue;
                var filePath = Paths.get(directoryPath, ev.context().toString());
                handleFileAsync(filePath);
            }
            key.reset();
        }
    }

    private void handleFileAsync(Path filePath) {
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                parser.parseCSV(filePath.toString());
                Files.delete(filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        })
                .start();
    }

    @SuppressWarnings("unchecked")
    private <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>) event;
    }
}
