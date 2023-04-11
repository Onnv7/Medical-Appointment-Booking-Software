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
export const updateAvatarDoctorDefault = async (req, res, next) => {
    try {
        let type;
        let image, avatarUrl = "";
        if (req.file) {
            type = req.file.mimetype
            image = `data:${type};base64,` + req.file.buffer.toString('base64');
            const uploadedResponse = await cloudinary.uploader.upload(image, {
                public_id: "doctor_avatar_default",
                upload_preset: 'doctor avatar'
            })
        }

        res.status(200).json({ success: true, message: "Uploaded avatar" })
    } catch (error) {
        next(error)
    }
}