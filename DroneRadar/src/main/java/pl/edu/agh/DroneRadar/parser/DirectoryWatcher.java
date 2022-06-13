package pl.edu.agh.DroneRadar.parser;

import org.springframework.stereotype.Service;

import java.nio.file.*;

@Service
public class DirectoryWatcher {
    private final String directoryPath = "C:\\Users\\MichalSkorek\\Documents\\droneradar\\DroneRadar\\DroneRadar\\src\\flightData";
    private final Parser parser;

    public DirectoryWatcher(Parser parser) {
        this.parser = parser;

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
