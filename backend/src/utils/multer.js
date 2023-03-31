import multer from 'multer';
import path from 'path';
import { fileURLToPath } from 'url';

const avatarStorage = multer.diskStorage({
    destination: function (req, file, cb) {
        const __filename = fileURLToPath(import.meta.url);
        const __dirname = path.dirname(__filename);

        const uploadDir = path.join(__dirname, '..', '..', 'uploads', 'images', 'avatar');
        cb(null, uploadDir);
    },
    filename: function (req, file, cb) {
        cb(null, `${req.params.patientId}.jpg`);
    }
});

const specialistStorage = multer.diskStorage({
    destination: function (req, file, cb) {
        const __filename = fileURLToPath(import.meta.url);
        const __dirname = path.dirname(__filename);
        const uploadDir = path.join(__dirname, '..', '..', 'uploads', 'images', 'specialist');
        cb(null, uploadDir);
    },
    filename: function (req, file, cb) {
        console.log(req.body)
        cb(null, Date.now() + '-' + file.originalname);
    }
});

export const uploadAvatar = multer({ storage: avatarStorage });
export const uploadSpecialist = multer({ storage: specialistStorage });

