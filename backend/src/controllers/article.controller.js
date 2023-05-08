import Article from "../models/article.model.js";


export const createArticle = async (req, res, next) => {
    try {
        const article = new Article({
            ...req.body
        });
        await article.save();
        res.status(200).json({ success: true, message: "Created period successfully" })
    } catch (error) {
        next(error);
    }
}

export const getAllArticle = async (req, res, next) => {
    try {
        const articles = await Article.find();

        res.status(200).json({ success: true, message: "Get all successfully", result: articles })
    } catch (error) {
        next(error);
    }
}

