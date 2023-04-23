import express from "express";
import { createSpecialist, removeSpecialist, updateSpecialist, getAllSpecialists, getSomeSpecialists } from "../controllers/specialist.controller.js"
import multer from 'multer';
const router = express.Router();

const storage = multer.memoryStorage();
const upload = multer({ storage });

router.post("/create", upload.single('image'), createSpecialist);
router.get("/list", getAllSpecialists);
router.get("/some/:quantity", getSomeSpecialists);
router.delete("/remove/:specialistId", removeSpecialist);
router.patch("/update/:specialistId", updateSpecialist);


export default router;