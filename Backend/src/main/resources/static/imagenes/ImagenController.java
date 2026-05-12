@RestController
@RequestMapping("/api/imagenes")
public class ImagenController {

    private final ImagenService imagenService;

    public ImagenController(ImagenService imagenService) {
        this.imagenService = imagenService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadImagen(@RequestParam("file") MultipartFile file) {
        String url = imagenService.guardarImagen(file);

        Map<String, String> response = new HashMap<>();
        response.put("url", url);

        return ResponseEntity.ok(response);
    }
}
