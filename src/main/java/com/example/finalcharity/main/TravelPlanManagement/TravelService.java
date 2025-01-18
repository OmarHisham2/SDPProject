import com.example.finalcharity.main.Campaign.Campaign;
import com.example.finalcharity.main.Campaign.CampaignService;
import com.example.finalcharity.main.User.User;
import com.example.finalcharity.main.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TravelService {
    private final TravelRepository travelRepository;
    private final TravelExpenseRepository expenseRepository;
    private final CampaignService campaignService;
    private final UserService userService;

    @Autowired
    public TravelService(TravelRepository travelRepository, 
                        TravelExpenseRepository expenseRepository,
                        CampaignService campaignService,
                        UserService userService) {
        this.travelRepository = travelRepository;
        this.expenseRepository = expenseRepository;
        this.campaignService = campaignService;
        this.userService = userService;
    }

    // Create new travel plan
    public TravelPlan createTravelPlan(TravelPlan plan, Long campaignId) {
        Campaign campaign = campaignService.getCampaignDetails(campaignId);
        plan.setRelatedCampaign(campaign);
        plan.setStatus(TravelStatus.PLANNED);
        return travelRepository.save(plan);
    }

    // Get all travel plans
    public List<TravelPlan> getAllTravelPlans() {
        return travelRepository.findAll();
    }

    // Get travel plan by ID
    public Optional<TravelPlan> getTravelPlanById(Long id) {
        return travelRepository.findById(id);
    }

    // Update travel plan
    public TravelPlan updateTravelPlan(Long id, TravelPlan updatedPlan) {
        TravelPlan existingPlan = travelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Travel plan not found"));

        existingPlan.setTitle(updatedPlan.getTitle());
        existingPlan.setDescription(updatedPlan.getDescription());
        existingPlan.setStartDate(updatedPlan.getStartDate());
        existingPlan.setEndDate(updatedPlan.getEndDate());
        existingPlan.setDestination(updatedPlan.getDestination());
        existingPlan.setBudget(updatedPlan.getBudget());
        
        return travelRepository.save(existingPlan);
    }

    // Delete travel plan
    public void deleteTravelPlan(Long id) {
        travelRepository.deleteById(id);
    }

    // Add expense to travel plan
    public TravelExpense addExpense(Long travelId, TravelExpense expense) {
        TravelPlan plan = travelRepository.findById(travelId)
                .orElseThrow(() -> new IllegalArgumentException("Travel plan not found"));
        
        expense.setTravelPlan(plan);
        return expenseRepository.save(expense);
    }

    // Get all expenses for a travel plan
    public List<TravelExpense> getTravelExpenses(Long travelId) {
        return expenseRepository.findByTravelPlanId(travelId);
    }

    // Update travel status
    public TravelPlan updateStatus(Long travelId, TravelStatus newStatus) {
        TravelPlan plan = travelRepository.findById(travelId)
                .orElseThrow(() -> new IllegalArgumentException("Travel plan not found"));
        
        plan.setStatus(newStatus);
        return travelRepository.save(plan);
    }

    // Add participant to travel plan
    public TravelPlan addParticipant(Long travelId, Integer userId) {
        TravelPlan plan = travelRepository.findById(travelId)
                .orElseThrow(() -> new IllegalArgumentException("Travel plan not found"));
        
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        plan.getParticipants().add(user);
        return travelRepository.save(plan);
    }

    // Calculate total expenses
    public double calculateTotalExpenses(Long travelId) {
        List<TravelExpense> expenses = expenseRepository.findByTravelPlanId(travelId);
        return expenses.stream()
                .mapToDouble(TravelExpense::getAmount)
               .sum();}
}