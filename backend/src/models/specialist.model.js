import mongoose from "mongoose";
import Doctor from "./doctor.model.js";

const specialistSchema = mongoose.Schema({
    name: {
        type: String,
        unique: true,
    },
    imageUrl: {
        type: String
    }
}, { timestamps: false });

specialistSchema.virtual('doctorQuantity').get(async function () {
    const doctors = await Doctor.find({ specialist: this._id });
    console.log(doctor?.length, "nè")
    if (!doctors)
        return 0;
    return doctors.length;
});

export default mongoose.model("Specialist", specialistSchema);