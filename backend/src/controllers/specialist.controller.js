import Specialist from "../models/specialist.model.js";
import cloudinary from "../utils/cloudinary.js";

export const getAllSpecialists = async (req, res, next) => {
    try {
        const specialists = await Specialist.aggregate([
            {
                $lookup: {
                    from: "doctors", // Tên collection của Doctor model
                    localField: "_id",
                    foreignField: "specialist",
                    as: "doctors",
                },
            },
            {
                $project: {
                    _id: 1,
                    name: 1,
                    imageUrl: 1,
                    doctorQuantity: { $size: "$doctors" }, // Tính kích thước của mảng doctors để lấy số lượng bác sĩ
                },
            },
        ]);
        res.status(200).json({ success: true, message: "Get all specialist successfully!", result: specialists });
    } catch (error) {
        next(error);
    }
}
export const getSomeSpecialists = async (req, res, next) => {
    try {
        const list = await Specialist.find().limit(req.params.quantity);
        res.status(200).json({ success: true, message: "Get all specialist successfully!", result: list });
    } catch (error) {
        next(error);
    }
}

export const createSpecialist = async (req, res, next) => {
    try {
        console.log(req.body)
        const name = req.body.name
        let type;
        let image, avatarUrl = "";
        if (req.file) {
            type = req.file.mimetype
            image = `data:${type};base64,` + req.file.buffer.toString('base64');
            const uploadedResponse = await cloudinary.uploader.upload(image, {
                public_id: name,
                upload_preset: 'specialist'
            })
            avatarUrl = uploadedResponse.url
        }
        const imageUrl = avatarUrl
        const specialist = new Specialist(
            { ...req.body, imageUrl }
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