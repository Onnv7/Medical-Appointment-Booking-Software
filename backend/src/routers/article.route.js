import express from "express";
import { createArticle, getAllArticle } from "../controllers/article.controller.js";
const router = express.Router();

router.post('/create-new', createArticle)
router.get('/get-all', getAllArticle)

export default router;