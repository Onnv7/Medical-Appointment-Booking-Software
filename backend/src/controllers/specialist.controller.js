import Specialist from "../models/specialist.model.js";


export const getAllSpecialists = async (req, res, next) => {
    try {
        const list = await Specialist.find();
        res.status(200).json({ success: true, message: "Get all specialist successfully!", result: list });
    } catch (error) {
        next(error);
    }
}

export const createSpecialist = async (req, res, next) => {
    try {
        const specialist = new Specialist(
            { ...req.body }
        );
        await specialist.save();
        res.status(200).json({ success: true, message: "Created specialist", result: specialist })
    } catch (error) {
        next(error);
    }
}

export const removeSpecialist = async (req, res, next) => {
    try {
        const specialist = await Specialist.findByIdAndDelete(req.params.specialistId);

        res.status(200).json({ success: true, message: "Removed Specialist" });
    } catch (error) {
        next(error);
    }
}

export const updateSpecialist = async (req, res, next) => {
    try {
        const specialist = await Specialist.findByIdAndUpdate(
            req.params.specialistId,
            { ...req.body },
            { new: true }
        );
        res.status(200).json({ success: true, message: "Updated Specialist", result: specialist });
    } catch (error) {
        next(error);
    }
}