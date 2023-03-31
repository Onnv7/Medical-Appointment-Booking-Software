import Period from "../models/period.model.js";


export const createPeriod = async (req, res, next) => {
    try {
        const period = new Period({
            ...req.body
        });
        await period.save();
        res.status(200).json({ success: true, message: "Created period successfully" })
    } catch (error) {
        next(error);
    }
}