import mongoose from "mongoose";

const patientSchema = mongoose.Schema({
    email: {
        type: String,
        unique: true,
        required: true,
    },
    password: {
        type: String,
        required: true,
    },
    name: {
        type: String,
        required: true,
    },
    gender: {
        type: String,
        enum: ["male", "female"],
        default: "male",
    },
    birthDate: {
        type: Date,
        default: null,
    },
    avatarUrl: {
        type: String,
        default: "https://res.cloudinary.com/dtvnsczg8/image/upload/v1681464004/Clinic/avatar/patient/patient_avatar_default.png",
    },
    phone: {
        type: String,
        default: "",
    },
    address: {
        type: String,
        default: "",
    }
}, { timestamps: true });

export default mongoose.model("Patient", patientSchema);